package ru.kazantsev.nsd.scripts.eventActions.startSchedulerFromUserInterface

/**
 * Автор Казанцев
 * Название Вычисление описания выбранной задачи планировщика
 * Назначение как и в описании
 */

String LANG_CODE = 'ru'
String DATE_FORMAT = 'dd.MM.yyyy HH:mm:ss'

def PARAMS_FOR_UPDATE_ON_FORMS = ['task']
if (form == null) return PARAMS_FOR_UPDATE_ON_FORMS

if(form.task == null) return ""
def task = api.scheduler.schedulingService.getSchedulerTask(form.task.code)


String text = """
<p><b>Последняя дата выполнения: </b>${task.lastExecutionDate?.format(DATE_FORMAT)}</p>
<p><b>Cледующая дата выполнения: </b>${task.planDate?.format(DATE_FORMAT)}</p>
"""

if(task.trigger.size() > 0) {
    text += "<p><b>Рассписание:</b>"
    task.trigger.each{
        text += "<br>${it.title} Cледующая дата выполнения: ${it.planExecutionDate?.format(DATE_FORMAT)}"
    }
    text += '</p>'
}

if(task.description.size() > 0) {
    def description = task.description.find{it.getLang() == LANG_CODE}
    if(description != null) {
        String value = description.getValue()
        if(value != null && value.size() > 0) {
            text += "<p> <b>Описание: </b><br> ${value}</p>"
        }
    }
}
return text