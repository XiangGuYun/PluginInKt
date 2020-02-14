package base.constant

import javafx.stage.StageStyle

@Target(AnnotationTarget.CLASS)
annotation class LayoutId(val id: String)

@Target(AnnotationTarget.CLASS)
annotation class AppTitle(val title: String)

@Target(AnnotationTarget.CLASS)
annotation class AppIcon(val iconPath: String)

@Target(AnnotationTarget.CLASS)
annotation class Resizable(val resizable: Boolean = true)

@Target(AnnotationTarget.CLASS)
annotation class Style(val style: StageStyle)

