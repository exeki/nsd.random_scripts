package ru.kazantsev.nsd.scripts.eventActions.getDictionaryFromUserInterface

/**
 * Автор Казанцев
 * Название: Вычисление перечня справочников
 * Назначение: собирает произвольный список элементов типов справочников
 */

Map result = [:]
def parent = api.metainfo.getMetaClass('catalogItem')
parent.children.each{
    result.put(it.code,  api.metainfo.getMetaClassTitle(it.code) ?: it.title ?: it.code)
}
return result