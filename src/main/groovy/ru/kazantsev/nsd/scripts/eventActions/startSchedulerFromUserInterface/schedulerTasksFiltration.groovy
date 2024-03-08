package ru.kazantsev.nsd.scripts.eventActions.startSchedulerFromUserInterface

import static ru.kazantsev.nsd.sdk.global_variables.ApiPlaceholder.*

/**
 * Название: Фильтрация задач планировщика
 */

def PARAMS_FOR_UPDATE_ON_FORMS = ['types', 'onlyPlanned']
if (form == null) return PARAMS_FOR_UPDATE_ON_FORMS

String SCHEDULER_OBJECT_HOLDER_METACODE = 'catalogs$scheduler'
String LANG_CODE = 'ru'
String TYPE_ATTR_CODE = 'cstring'
String IS_PLANNED_ATTR_CODE = 'cBool1'
List<String> ONLY_TYPES = ['ReceiveMailTask', 'ExecuteScriptTask']

api.tx.call {
    List<Map> existedObjects = [].plus(utils.find(SCHEDULER_OBJECT_HOLDER_METACODE, [:]))
    List currentTasks = api.scheduler.schedulingService.getSchedulerTasks()

    currentTasks.each { currentTask ->
        String taskTitle
        if (currentTask.title.size() > 0) taskTitle = currentTask.title.find { it.getLang() == LANG_CODE }.getValue()
        else taskTitle = currentTask.code
        Map attrs = [
                'removed'       : false,
                'code'          : currentTask.code,
                'title'         : taskTitle,
                (TYPE_ATTR_CODE): currentTask.type,
                (IS_PLANNED_ATTR_CODE) : (currentTask.planDate != null)
        ]

        Map existedObject = existedObjects.find { it.code == currentTask.code }
        if (existedObject != null) {

            utils.edit(existedObject, attrs)
            existedObjects.remove(existedObject)
        } else utils.create(SCHEDULER_OBJECT_HOLDER_METACODE, attrs)

    }
    existedObjects.findAll { it.removed == false }.each { utils.edit(it, ['removed': true]) }
}

List<Map> result = utils.find(SCHEDULER_OBJECT_HOLDER_METACODE, [:])
if(form.onlyPlanned) result = result.findAll{it[(IS_PLANNED_ATTR_CODE)] == true}
if(form.types != null && form.types.size() > 0) result = result.findAll{it[(TYPE_ATTR_CODE)] in form.types.keySet()}
if(ONLY_TYPES != null && ONLY_TYPES.size() > 0) result = result.findAll{it[(TYPE_ATTR_CODE)] in ONLY_TYPES}
return result