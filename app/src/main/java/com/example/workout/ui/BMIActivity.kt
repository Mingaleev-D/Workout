package com.example.workout.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.workout.R
import com.example.workout.databinding.ActivityBmiactivityBinding
import com.google.android.material.snackbar.Snackbar
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

   private val binding by lazy { ActivityBmiactivityBinding.inflate(layoutInflater) }

   companion object {
      private const val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW" //Metric view
      private const val US_UNITS_VIEW = "US_UNIT_VIEW"         //US view
   }

   private var currentVisibleView = METRIC_UNITS_VIEW

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(binding.root)

      setSupportActionBar(binding.toolbarBmiActivity)
      if (supportActionBar != null) {
         supportActionBar?.setDisplayHomeAsUpEnabled(true)
      }
      binding.toolbarBmiActivity.setNavigationOnClickListener {
         onBackPressed()
      }

      makeVisibleMetricUnitsView()
      binding.unitsRg.setOnCheckedChangeListener { _, checkedId ->
         if(checkedId == R.id.metric_unit_rb){
            makeVisibleMetricUnitsView()
         }else
            makeVisibleUSUnitsView()
      }

      binding.calculateUnitsBtn.setOnClickListener {
         if (validateMetricUnits()) {
            val heightValue: Float = binding.metricUnitHeightEt.text.toString().toFloat() / 100
            val weightValue: Float = binding.metricUnitWeightEt.text.toString().toFloat()
            val bmi = weightValue / (heightValue * heightValue)
            displayBMIResult(bmi)
         } else {
            Snackbar.make(binding.root, "Пожалуйста, введите данные", Snackbar.LENGTH_LONG).show()
         }
      }
   }

   private fun makeVisibleMetricUnitsView() {
      currentVisibleView = METRIC_UNITS_VIEW

      binding.apply {
         metricUnitsViewLl.visibility = View.VISIBLE
         usUnitsViewLl.visibility = View.GONE

         metricUnitWeightEt.text!!.clear()
         metricUnitHeightEt.text!!.clear()

         displayBmiResultLl.visibility = View.INVISIBLE
      }
   }
   private fun makeVisibleUSUnitsView() {
      currentVisibleView = US_UNITS_VIEW
      binding.apply {
         metricUnitsViewLl.visibility = View.GONE
         usUnitsViewLl.visibility = View.VISIBLE

         usUnitWeightEt.text!!.clear()
         usUnitHeightFeetEt.text!!.clear()
         usUnitHeightInchEt.text!!.clear()

         displayBmiResultLl.visibility = View.INVISIBLE
      }
   }

   private fun displayBMIResult(bmi: Float) {
      binding.displayBmiResultLl.visibility = View.VISIBLE

      val bmiLabel: String
      val bmiDescription: String
      if (bmi.compareTo(15f) <= 0) {
         bmiLabel = "очень сильно мало весит"
         bmiDescription = "Ой! Вам действительно нужно лучше заботиться о себе! Ешьте больше"
      } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0) {
         bmiLabel = "сильно недостаточно веса"
         bmiDescription = "Ой! Вам действительно нужно лучше заботиться о себе! Ешьте больше"
      } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0) {
         bmiLabel = "Ниже среднего"
         bmiDescription = "Ой! Вам действительно нужно лучше заботиться о себе! Ешьте больше"
      } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0) {
         bmiLabel = "Все в норме"
         bmiDescription = "Поздравляю! ты в неплохой форме форме"
      } else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0) {
         bmiLabel = "Избыточный веc"
         bmiDescription = "Ой! Вам действительно нужно позаботиться о себе! Тренировка может быть!"
      } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0) {
         bmiLabel = "Умеренное ожирение"
         bmiDescription = "Ой! Вам действительно нужно позаботиться о себе! Тренировка может быть!"
      } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0) {
         bmiLabel = "сильное ожирение"
         bmiDescription = "МОЙ БОГ! Вы находитесь в очень опасном состоянии! Действовать сейчас!"
      } else {
         bmiLabel = "Очень сильное ожирение"
         bmiDescription = "МОЙ БОГ! Вы находитесь в очень опасном состоянии! Действовать сейчас!"
      }

      val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()
      binding.bmiResultTv.text = bmiValue
      binding.bmiTypeTv.text = bmiLabel
      binding.bmiDescriptionTv.text = bmiDescription
   }

   private fun validateMetricUnits(): Boolean {
      var isValue = true

      if (binding.metricUnitWeightEt.text.toString().isEmpty()) {
         isValue = false
      } else if (binding.metricUnitHeightEt.text.toString().isEmpty()) {
         isValue = false
      }
      return isValue


   }
}