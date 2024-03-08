package ru.kazantsev.nsd.scripts.eventActions.startSchedulerFromUserInterface

import static ru.kazantsev.nsd.sdk.global_variables.ApiPlaceholder.*
import static ru.kazantsev.nsd.sdk.global_variables.GlobalVariablesPlaceholder.*

/**
 * Автор: Казанцев
 * Дата создания: 24.02.2024
 * Название: Запуск планировщика из пользовательского интерфейса
 * Назначение: как и в названии)
 */

String DATE_FORMAT = 'dd.MM.yyyy HH:mm:ss.SSS'

Date startDate = new Date()

void doComment(String message) {
    utils.create("comment", ["source" : params.task, "private" : true, "text" : message])
}

try {
    api.scheduler.run(params.task.code)
    Date endDate = new Date()
    String comment = "<span style=\"color: green;\">Успешно завершено выполнение инициированное пользователем.</span><br> " +
            "<b>Выполнение инциировал:</b> ${user != null ? user.title : "Суперпользователь"}<br> " +
            "<b>Начало выполнения:</b> ${startDate.format(DATE_FORMAT)}<br> " +
            "<b>Завершение выпонления:</b> ${endDate.format(DATE_FORMAT)}<br>"
    doComment(comment)
    result.showMessage("Успешно", "Задача успешно выполнена.")
} catch (Exception e) {
    Date endDate = new Date()
    String comment = "<span style=\"color: red;\">Выполнение инициированное пользователем завершилось с ошибкой.</span><br> " +
            "<b>Выполнение инциировал:</b> ${user != null ? user.title : "Суперпользователь"}<br> " +
            "<b>Начало выполнения:</b> ${startDate.format(DATE_FORMAT)}<br> " +
            "<b>Завершение выпонления:</b> ${endDate.format(DATE_FORMAT)}<br>" +
            "<b>Текст ошибки:</b> ${e.getClass().getSimpleName()}: ${e.message}<br>"
    doComment(comment)
    result.showMessage("Ошибка", "При выполнении задачи планировщика произошла ошибка: ${e.getClass().getSimpleName()}: ${e.message}")
}
