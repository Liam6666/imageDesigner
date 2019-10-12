package yzx.app.image.design.ui

import android.app.Activity
import android.graphics.Color


interface IImageDesignActivity


fun IImageDesignActivity.makeStatusBar() {
    (this as Activity).window.statusBarColor = Color.BLACK
}
