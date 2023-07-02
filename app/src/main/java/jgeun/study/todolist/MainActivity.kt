package jgeun.study.todolist

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import jgeun.study.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

	private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
	private val viewModel by viewModels<TaskViewModel>()
	private var taskAdapter: TaskAdapter? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)

		initUI()
		observeData()
	}

	private fun initUI() {

		taskAdapter = TaskAdapter(
			taskFinishEvent = { task -> viewModel.finishTask(task) },
			taskDeleteEvent = { task -> viewModel.deleteTask(task) }
		)

		binding.btnAdd.setOnClickListener {
			TaskDialog{ title, content, bgColor ->
				viewModel.addTask(title, content, bgColor)
			}.show(supportFragmentManager, "TaskDialog")
		}

		with(binding.rvTask) {
			setHasFixedSize(true)
			adapter = taskAdapter
			layoutManager = LinearLayoutManager(this@MainActivity)
			addItemDecoration(TaskItemDecoration(40))
		}
	}

	private fun observeData() {
		viewModel.taskLiveData.observe(this) { taskList ->
			binding.taskTitle.text = resources.getString(R.string.task_title, taskList.size)

			// 완료한 개수 / 전체 비율 -> ex) 0/3 완료
			val taskFinishCnt = viewModel.getTaskFinishCnt()
			val taskTotalCnt = viewModel.getTotalTaskCnt()
			binding.tvProgressRate.text = resources.getString(R.string.progress_task_rate, taskFinishCnt, taskTotalCnt)

			// progressbar 설정
			with(binding.pbProgress) {
				progress = taskFinishCnt
				max = taskTotalCnt
			}

			val percent = if (taskTotalCnt == 0) 0 else taskFinishCnt*100/taskTotalCnt
			binding.tvProgressPercent.text = resources.getString(R.string.progress_title, percent)

			// 설정된 Progress Rate에 따라 Text 변경 정책
			binding.tvProgressTitle.text = getProgressTitle(percent)

			// TaskMsg는 Task가 존재할 때는 보이지 않습니다
			binding.taskMsg.text = getTaskMsg(taskTotalCnt)
			binding.taskMsg.visibility = if (taskList.isEmpty()) View.VISIBLE else View.INVISIBLE

			taskAdapter?.submitList(taskList)
		}
	}

	private fun getProgressTitle(percent: Int): String {
		return if (percent == 100) {
			 resources.getString(R.string.progress_title_finish)
		} else if (percent >= 30) {
			resources.getString(R.string.progress_title_ing)
		} else {
			resources.getString(R.string.progress_title_start)
		}
	}

	private fun getTaskMsg(totalCnt: Int): String {
		return if (totalCnt == 0)
			resources.getString(R.string.task_empty_msg)
		else
			resources.getString(R.string.task_done_msg)
	}
}