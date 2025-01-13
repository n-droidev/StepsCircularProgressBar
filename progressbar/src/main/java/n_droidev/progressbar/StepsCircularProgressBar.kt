package n_droidev.progressbar

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.use
import androidx.core.graphics.toColorInt
import kotlin.math.max

val Float.dp get() = (this * Resources.getSystem().displayMetrics.density + 0.5f)

/**
 * Author: Samir Alakbarov (https://github.com/n-droidev)
 * Created on: 09.01.25
 * Description: You can use XML attributes to set 2 type of tracks (done and undone).
 * You can set as many colors as you want in Kotlin side with function:
 * stepsCircularProgressBar.setData(totalSteps = 9, dynamicStepColors = intArrayOf(colorInts...)) // no need to doneSteps or undoneSteps when using function
 * You can modify progress steps corners (Square, Butt, Round). You can modify its thickness and gap between them too.
 *
 *
 * Copyright 2025 Samir Alakbarov
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
class StepsCircularProgressBar @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attributeSet, defStyleAttr) {

    companion object {
        const val DEFAULT_STEP_DONE_COLOR = "#67D39D"
        const val DEFAULT_STEP_UNDONE_COLOR = "#EB6A6E"
        const val DEFAULT_TOTAL_STEPS = 5
        const val DEFAULT_DONE_STEPS = 0
        const val DEFAULT_GAP_BETWEEN_STEPS = 0.8f
        const val DEFAULT_STEP_THICKNESS = 10f
        const val CORNER_TYPE_BUTT = 1
        const val CORNER_TYPE_SQUARE = 2
        const val CORNER_TYPE_ROUND = 3
        const val FULL_DEGREE_360 = 360
        const val ZERO = 0
        const val ZERO_FLOAT = 0f
        const val ONE = 1
        const val TWO_FLOAT = 2f
        const val START_DEGREE_270 = 270
        const val DEFAULT_SIZE = 100
    }

    private var stepStrokeType = Paint.Cap.ROUND
    private var stepThickness = DEFAULT_STEP_THICKNESS.dp
    private var stepDoneColor = DEFAULT_STEP_DONE_COLOR.toColorInt()
    private var stepUndoneColor = DEFAULT_STEP_UNDONE_COLOR.toColorInt()
    private var dynamicStepColors: IntArray = intArrayOf()
    private var totalSteps = DEFAULT_TOTAL_STEPS
    private var doneSteps = DEFAULT_DONE_STEPS
    private var startAngleExtra = ZERO_FLOAT
    private var gapBetweenEachStep = DEFAULT_GAP_BETWEEN_STEPS.dp
    private var angleLengthOfEachStep = ZERO_FLOAT

    private val rectF = RectF()
    private var circumference = ZERO_FLOAT
    private var gapAngle = ZERO_FLOAT

    private val stepDonePaint
        get() = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            color = stepDoneColor
            strokeWidth = stepThickness
            strokeCap = stepStrokeType
        }

    private val stepUndonePaint
        get() = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            color = stepUndoneColor
            strokeWidth = stepThickness
            strokeCap = stepStrokeType
        }

    private val stepDynamicPaint
        get() = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            color = stepUndoneColor
            strokeWidth = stepThickness
            strokeCap = stepStrokeType
        }


    init {
        context.theme.obtainStyledAttributes(
            attributeSet, R.styleable.StepsCircularProgressBar, defStyleAttr, 0
        ).use {
            extractValues(it)
            setValues()
        }
    }

    private fun extractValues(typedArray: TypedArray) {
        typedArray.apply {
            totalSteps = getInteger(
                R.styleable.StepsCircularProgressBar_scp_stepsCount, DEFAULT_TOTAL_STEPS
            )

            stepDoneColor = getColor(
                R.styleable.StepsCircularProgressBar_scp_doneStepColor, DEFAULT_STEP_DONE_COLOR.toColorInt()
            )

            stepUndoneColor = getColor(
                R.styleable.StepsCircularProgressBar_scp_undoneStepColor, DEFAULT_STEP_UNDONE_COLOR.toColorInt()
            )

            gapBetweenEachStep = getDimension(
                R.styleable.StepsCircularProgressBar_scp_gapBetweenSteps, DEFAULT_GAP_BETWEEN_STEPS.dp
            )

            stepThickness = getDimension(
                R.styleable.StepsCircularProgressBar_scp_stepThickness, DEFAULT_STEP_THICKNESS.dp
            )

            startAngleExtra = getFloat(
                R.styleable.StepsCircularProgressBar_scp_stepExtraRotationAngle, ZERO_FLOAT
            )

            val strokeTypeEnum = getInteger(
                R.styleable.StepsCircularProgressBar_scp_stepCornerType, CORNER_TYPE_ROUND
            )
            stepStrokeType = when (strokeTypeEnum) {
                CORNER_TYPE_BUTT -> Paint.Cap.BUTT
                CORNER_TYPE_SQUARE -> Paint.Cap.SQUARE
                CORNER_TYPE_ROUND -> Paint.Cap.ROUND
                else -> Paint.Cap.ROUND
            }
        }
    }

    private fun setValues() {
        stepDonePaint.apply {
            color = stepDoneColor
            strokeWidth = stepThickness
        }
        stepUndonePaint.apply {
            color = stepUndoneColor
            strokeWidth = stepThickness
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val centerX = width / TWO_FLOAT
        val centerY = height / TWO_FLOAT
        val radius = (width - stepThickness) / TWO_FLOAT

        rectF.set(
            centerX - radius, centerY - radius, centerX + radius, centerY + radius
        )

        val extraAngles = FULL_DEGREE_360 * (stepThickness / TWO_FLOAT + gapBetweenEachStep) / circumference
        var start = START_DEGREE_270 + startAngleExtra + extraAngles / TWO_FLOAT
        for (i in ZERO..<totalSteps) {
            val paint = when (getStatus(doneSteps, i)) {
                State.DONE -> stepDonePaint
                State.UNDONE -> stepUndonePaint
                State.DYNAMIC -> {
                    stepDynamicPaint.also { paint ->
                        dynamicStepColors.getOrNull(i)?.let { color ->
                            paint.color = color
                        }
                    }
                }
            }

            canvas.drawArc(rectF, start, angleLengthOfEachStep, false, paint)
            start += angleLengthOfEachStep + gapAngle
        }
    }

    fun setData(totalSteps: Int? = null, doneSteps: Int = ZERO, dynamicStepColors: IntArray? = null) {
        if (totalSteps != null) {
            this.totalSteps = totalSteps
        }
        this.doneSteps = if (this.totalSteps >= doneSteps) {
            doneSteps
        } else {
            this.totalSteps
        }
        if (dynamicStepColors != null) {
            this.dynamicStepColors = dynamicStepColors
        }

        measureAngleLengthOfEachStep()
        postInvalidate()
    }

    private fun measureAngleLengthOfEachStep() {
        // local function declaration
        fun measure() {
            if (totalSteps == ZERO) {
                angleLengthOfEachStep = ZERO_FLOAT
                return
            } else if (totalSteps == ONE) {
                angleLengthOfEachStep = FULL_DEGREE_360.toFloat()
                return
            }
            circumference = (Math.PI * measuredWidth).toFloat()
            gapAngle = FULL_DEGREE_360 * gapBetweenEachStep / circumference
            val stepWidth = (FULL_DEGREE_360 / totalSteps) - gapAngle
            angleLengthOfEachStep = stepWidth
        }

        // calling local measure function
        if (measuredWidth <= ZERO) {
            this.post {
                measure()
            }
        } else {
            measure()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val desiredSize = DEFAULT_SIZE
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        //Measure Width
        val width: Int = when (widthMode) {
            MeasureSpec.EXACTLY -> {
                //Must be this size
                widthSize
            }

            MeasureSpec.AT_MOST -> {
                //Can't be bigger than...
                max(desiredSize, widthSize)
            }

            else -> {
                //Be whatever you want
                desiredSize
            }
        }


        //Measure Height
        val height: Int = when (heightMode) {
            MeasureSpec.EXACTLY -> {
                //Must be this size
                heightSize
            }

            else -> {
                //Be whatever you want
                width
            }
        }

        //MUST CALL THIS
        setMeasuredDimension(width, height)

        measureAngleLengthOfEachStep()
    }

    enum class State {
        DONE, UNDONE, DYNAMIC
    }

    private fun getStatus(success: Int, index: Int): State {
        return if (index < success) {
            State.DONE
        } else if (dynamicStepColors.isNotEmpty()) {
            State.DYNAMIC
        } else {
            State.UNDONE
        }
    }
}
