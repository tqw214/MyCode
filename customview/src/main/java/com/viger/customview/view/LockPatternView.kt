package com.viger.customview.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View



class LockPatternView : View {

    //val arrB = Array(3){Array(3){IntArray(3)}}

    private var mIsInit = false

    // 二维数组初始化，int[3][3]
    private var mPoints: Array<Array<Point?>> = Array(3) {
        Array<Point?>(3, {
            null
        })
    }

    constructor(context : Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private lateinit var mLinePaint : Paint
    private lateinit var mPressedPaint: Paint
    private lateinit var mErrorPaint: Paint
    private lateinit var mNormalPaint: Paint
    private lateinit var mArrowPaint: Paint

    // 外圆的半径
    private var mDotRadius = 0

    private var mOuterPressedColor = 0xff8cbad8.toInt()
    private val mInnerPressedColor = 0xff0596f6.toInt()
    private val mOuterNormalColor = 0xffd9d9d9.toInt()
    private val mInnerNormalColor = 0xff929292.toInt()
    private val mOuterErrorColor = 0xff901032.toInt()
    private val mInnerErrorColor = 0xffea0945.toInt()

    private var mIsTouchPoint  = false
    private var mSelectPoints = ArrayList<Point>()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        // 初始化九个宫格，onDraw 回调用多次
        if(!mIsInit) {
            initDot()
            initPaint()
            mIsInit = true
        }
        // 绘制九个宫格
        drawShow(canvas)

    }

    private fun drawShow(canvas: Canvas?) {

    }

    private fun initPaint() {
        // new Paint 对象 ，设置 paint 颜色
        // 线的画笔
        mLinePaint = Paint()
        mLinePaint.color = mInnerPressedColor
        mLinePaint.style = Paint.Style.STROKE
        mLinePaint.isAntiAlias = true
        mLinePaint.strokeWidth = (mDotRadius/9).toFloat()
        // 按下的画笔
        mPressedPaint = Paint()
        mPressedPaint.style = Paint.Style.STROKE
        mPressedPaint.isAntiAlias = true
        mPressedPaint.strokeWidth = (mDotRadius / 6).toFloat()
        // 错误的画笔
        mErrorPaint = Paint()
        mErrorPaint.style = Paint.Style.STROKE
        mErrorPaint.isAntiAlias = true
        mErrorPaint.strokeWidth = (mDotRadius / 6).toFloat()
        // 默认的画笔
        mNormalPaint = Paint()
        mNormalPaint.style = Paint.Style.STROKE
        mNormalPaint.isAntiAlias = true
        mNormalPaint.strokeWidth = (mDotRadius / 9).toFloat()
        // 箭头的画笔
        mArrowPaint = Paint()
        mArrowPaint.color = mInnerPressedColor
        mArrowPaint.style = Paint.Style.FILL
        mArrowPaint.isAntiAlias = true

    }

    //初始化点
    private fun initDot() {

    }

    class Point(var centerX : Int, var centerY : Int, var index : Int) {
        private var STATUS_NORMAL = 1
        private val STATUS_PRESSED = 2
        private val STATUS_ERROR = 3

        private var status = STATUS_NORMAL

        fun setStatusPressed() {
            status = STATUS_PRESSED
        }

        fun setStatusNormal() {
            status = STATUS_NORMAL
        }

        fun setStatusError() {
            status = STATUS_ERROR
        }

        fun statusIsPressed() : Boolean {
            return status == STATUS_PRESSED
        }

        fun statusIsError(): Boolean {
            return status == STATUS_ERROR
        }

        fun statusIsNormal(): Boolean {
            return status == STATUS_NORMAL
        }

    }


}