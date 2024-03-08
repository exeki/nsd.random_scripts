package ru.kazantsev.nsd.scripts.eventActions.getDictionaryFromUserInterface

/**
 * Автор: Казанцев
 * Название: Выгрузить пользовательский справочник из интерфейса оператора
 * Позволяет получить выгрузку справочников при помощи ДПС
 * по пользовательскому событию
 */

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook

Row setValues(Row row, Map<Integer, Object> values){
    values.each{ key, value ->
        Cell cell = row.getCell(key)
        if(cell == null) {
            cell = row.createCell(key)
        }
        cell.setCellValue(value)
    }
    return row
}

List<Map> allElements = utils.find(params.dictionary.key, [:])

Map root = utils.get('root', [:])
Workbook workbook = new XSSFWorkbook()
Sheet sheet = workbook.createSheet()
Row headRow = sheet.createRow(0)
setValues(
        headRow,
        [
                0 : "Код элемента",
                1 : "UUID элемента",
                2 : "Наименование элемента",
                3 : "Является папкой",
                4 : "В архиве",
                5 : "Код родетеля"
        ]
)

Integer newRowIterator = 1

allElements.each{
    Row newRow = sheet.createRow(newRowIterator)
    setValues(
            newRow,
            [
                    0 : it.code,
                    1 : it.UUID,
                    2 : it.title,
                    3 : it.folder,
                    4 : it.removed
            ]
    )
    Map parent = it.parent
    if(parent != null) setValues(newRow, [5 : parent.code])
    newRowIterator++;
}

ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()
workbook.write(byteArrayOutputStream)
byte[] workbookBytes = byteArrayOutputStream.toByteArray()
Map file = utils.attachFile(
        root,
        "${params.dictionary.value}.${new Date().format("dd.MM.yyyy HH.mm.ss")}.xlsx",
        'excel',
        null,
        workbookBytes
)
result.downloadFile(file)