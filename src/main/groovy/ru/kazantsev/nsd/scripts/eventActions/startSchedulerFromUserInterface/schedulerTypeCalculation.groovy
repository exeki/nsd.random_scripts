package ru.kazantsev.nsd.scripts.eventActions.startSchedulerFromUserInterface

/**
 * Название: Получить перечень типов планировищка
 */

Map TASK_TYPES = [
        'ReceiveMailTask' : "Правило обработки входящей почты",
        "ExecuteScriptTask" : "Скрипт",
        "AdvImportSchedulerTask" : "Синхронизация"
]

List<String> ONLY_TYPES = ['ReceiveMailTask', 'ExecuteScriptTask']

if(ONLY_TYPES != null && ONLY_TYPES.size() > 0) return TASK_TYPES.findAll{it.key in ONLY_TYPES}
else return TASK_TYPES

