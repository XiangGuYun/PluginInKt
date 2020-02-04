package base

@Target(AnnotationTarget.CLASS)
annotation class LayoutId(val id: String)

@Target(AnnotationTarget.CLASS)
annotation class AppTitle(val title: String)

@Target(AnnotationTarget.CLASS)
annotation class AppIcon(val iconPath: String)

@Target(AnnotationTarget.CLASS)
annotation class Resizable(val resizable: Boolean = true)

