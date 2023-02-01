package com.example.workout.common

import com.example.workout.R
import com.example.workout.model.ExerciseModel

object Constants {

   fun defaultExerciseList(): ArrayList<ExerciseModel> {
      val exerciseList = ArrayList<ExerciseModel>()

      val jumpingJacks = ExerciseModel(1,
          "Прыжки с махами",
          R.drawable.jumping_jacks,
          false,
          false)
      exerciseList.add(jumpingJacks)

      val wallSit = ExerciseModel(2,
          "Воздушный стул",
          R.drawable.wall_sit,
          false,
          false)
      exerciseList.add(wallSit)

      val pushUp = ExerciseModel(3,
          "Отжимания",
          R.drawable.pushups,
          false,
          false)
      exerciseList.add(pushUp)

      val abCrunch = ExerciseModel(4,
          "Упражнения на пресс",
          R.drawable.ab_crunch,
          false,
          false)
      exerciseList.add(abCrunch)

      val stepUp = ExerciseModel(5,
          "Шаг вверх на стул",
          R.drawable.stepup,
          false,
          false)
      exerciseList.add(stepUp)

      val squat = ExerciseModel(6,
          "Приседание",
          R.drawable.squat,
          false,
          false)
      exerciseList.add(squat)

      val tricepsDip = ExerciseModel(7,
          "Отжимания на трицепс на стуле",
          R.drawable.triceps_dip,
          false,
          false)
      exerciseList.add(tricepsDip)

      val plank = ExerciseModel(8,
          "Планка",
          R.drawable.plank,
          false,
          false)
      exerciseList.add(plank)

      val highKnees = ExerciseModel(9,
          "Бег на месте с высокими коленями",
          R.drawable.high_knees,
          false,
          false)
      exerciseList.add(highKnees)

      val lunge = ExerciseModel(10,
          "Выпады вперед",
          R.drawable.lunge,
          false,
          false)
      exerciseList.add(lunge)

      val pushUpAndRotation = ExerciseModel(11,
          "Отжимания с вращением",
          R.drawable.pushup_and_rotation,
          false,
          false)
      exerciseList.add(pushUpAndRotation)

      val sidePlank = ExerciseModel(12,
          "Боковая планка",
          R.drawable.side_plank,
          false,
          false)
      exerciseList.add(sidePlank)

      return exerciseList
   }
}