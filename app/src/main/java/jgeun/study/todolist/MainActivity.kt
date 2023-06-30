package jgeun.study.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import jgeun.study.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

	private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)
	}
}