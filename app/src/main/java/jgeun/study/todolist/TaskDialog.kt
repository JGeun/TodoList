package jgeun.study.todolist

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.DialogFragment
import jgeun.study.todolist.databinding.DialogTaskBinding

class TaskDialog(
	private val addTask: (String, String, TaskBgColor) -> Unit
) : DialogFragment() {

	private lateinit var binding: DialogTaskBinding
	private lateinit var selectedBoxCheckView: ImageView
	private var selectedColor: TaskBgColor = TaskBgColor.PURPLE

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		binding = DialogTaskBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.dialogTaskBtnStore.setOnClickListener {
			val title = binding.dialogTaskEtTitle.text.toString()
			val content = binding.dialogTaskEtContent.text.toString()
			addTask(title, content, selectedColor)
			dismissAllowingStateLoss()
		}

		binding.dialogTaskBtnCancel.setOnClickListener {
			dismissAllowingStateLoss()
		}

		initBgColorEvent()
	}

	override fun onResume() {
		super.onResume()

		// xml에서 테두리 변경 및 크기 조절이 안되기에 적용했습니다.
		dialogLayoutSetting()
	}

	private fun initBgColorEvent() {
		selectedBoxCheckView = binding.dialogPurpleBoxCheck

		binding.dialogPurpleBox.setOnClickListener { setBoxCheckVisibility(binding.dialogPurpleBoxCheck, TaskBgColor.PURPLE) }
		binding.dialogGreenBox.setOnClickListener { setBoxCheckVisibility(binding.dialogGreenBoxCheck, TaskBgColor.GREEN) }
		binding.dialogBlueBox.setOnClickListener { setBoxCheckVisibility(binding.dialogBlueBoxCheck, TaskBgColor.BLUE) }
		binding.dialogSkyBox.setOnClickListener { setBoxCheckVisibility(binding.dialogSkyBoxCheck, TaskBgColor.SKY) }
		binding.dialogEmeraldBox.setOnClickListener { setBoxCheckVisibility(binding.dialogEmeraldBoxCheck, TaskBgColor.EMERALD) }
	}

	private fun setBoxCheckVisibility(selectedBoxCheck: ImageView, selectedColor: TaskBgColor) {
		// 이전 선택된 박스 체크 이미지의 visibility Gone
		selectedBoxCheckView.visibility = View.GONE

		// 이번에 선택된 check 이미지 설정
		selectedBoxCheckView = selectedBoxCheck
		selectedBoxCheckView.visibility = View.VISIBLE
		this.selectedColor = selectedColor
	}

	private fun dialogLayoutSetting() {
		val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
		params?.width = (getScreenWidth() * 0.9).toInt()
		dialog?.window?.run {
			attributes = params as WindowManager.LayoutParams
			setBackgroundDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.bg_task_dialog))
		}
	}

	private fun getScreenWidth(): Int {
		val windowManager = requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
		return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
			val windowMetrics = windowManager.currentWindowMetrics
			val insets = windowMetrics.windowInsets
				.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
			windowMetrics.bounds.width() - insets.left - insets.right
		} else {
			val displayMetrics = DisplayMetrics()
			windowManager.defaultDisplay.getMetrics(displayMetrics)
			displayMetrics.widthPixels
		}
	}
}