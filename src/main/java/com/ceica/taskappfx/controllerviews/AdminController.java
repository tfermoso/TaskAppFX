package com.ceica.taskappfx.controllerviews;

import com.ceica.taskappfx.controller.TaskController;

public class AdminController implements IControllerView {
    private TaskController taskController;
    @Override
    public void setTaskController(TaskController taskController) {
        this.taskController=taskController;
    }
}
