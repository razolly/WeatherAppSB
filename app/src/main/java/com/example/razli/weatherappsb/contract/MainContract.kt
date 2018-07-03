package com.example.razli.weatherappsb.contract

interface MainContract {

    interface View  {

        fun initView()

        fun setViewData(viewData: String)
    }

    interface Model {

        fun getData(): String

    }

    interface Presenter  {

        fun addNewTask(newTask: String)

        fun onClick(view: android.view.View)
    }
}


/*

abstract fun setLoadingIndicator(active: Boolean)

abstract fun showTasks(tasks: List<Task>)

abstract fun showAddTask()

abstract fun showTaskDetailsUi(taskId: String)

abstract fun showTaskMarkedComplete()

abstract fun showTaskMarkedActive()

abstract fun showCompletedTasksCleared()

abstract fun showLoadingTasksError()

abstract fun showNoTasks()

abstract fun showActiveFilterLabel()

abstract fun showCompletedFilterLabel()

abstract fun showAllFilterLabel()

abstract fun showNoActiveTasks()

abstract fun showNoCompletedTasks()

abstract fun showSuccessfullySavedMessage()

abstract fun isActive(): Boolean

abstract fun showFilteringPopUpMenu()
}

interface Presenter extends BasePresenter {

    abstract fun result(requestCode: Int, resultCode: Int)

    abstract fun loadTasks(forceUpdate: Boolean)

    abstract fun addNewTask()

    abstract fun openTaskDetails(requestedTask: Task)

    abstract fun completeTask(completedTask: Task)

    abstract fun activateTask(activeTask: Task)

    abstract fun clearCompletedTasks()

    abstract fun setFiltering(requestType: TasksFilterType)

    abstract fun getFiltering(): TasksFilterType */
