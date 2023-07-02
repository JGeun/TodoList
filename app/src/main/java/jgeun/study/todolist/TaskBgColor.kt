package jgeun.study.todolist

import androidx.annotation.DrawableRes

enum class TaskBgColor(@DrawableRes val background: Int) {
	PURPLE(R.drawable.bg_task_purple),
	GREEN(R.drawable.bg_task_green),
	BLUE(R.drawable.bg_task_blue),
	SKY(R.drawable.bg_task_sky),
	EMERALD(R.drawable.bg_task_emerald);
}