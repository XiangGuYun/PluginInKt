package base.layout

import base.constant.Window
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.effect.Effect
import javafx.scene.effect.Lighting
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.paint.Paint
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.scene.text.Text
import javafx.scene.text.TextFlow
import sample.base.BaseApp

/**
 * 文本编排容器
 * Text中的文字会根据窗口大小自动调整
 */
class TextFlowDemo : BaseApp() {
    override fun init(window: Window) {
        window.size(900, 720)
        window.scene = Scene(TextFlow().apply {
            bgColor("#ffffff")
            addChildren(Text("美联航暂停往返中国4座城市的航班  直至4月24日\n").apply {
                font = Font.font("", FontWeight.BOLD, 30.0)
                fill = Paint.valueOf("#ff6666")
                effect = Lighting()
//                textAlignment = TextAlignment.CENTER
//                textOrigin = VPos.CENTER
            }, Label().preSize(window.width, 50), Text("        中新网2月14日电 据美国联合航空公司网站消息，在评估了美国往返北京、成都、香港和上海的航班运营情况后，美联航决定暂停这些航班，直至4月24日。\n" +
                    "\n" +
                    "        通知称，美联航将对过去14天内访问过中国的旅客进行旅游限制。美国公民或美国合法永久居民的乘客抵达美国时须遵守额外的限制与健康筛查。过去14天内访问过中国大陆的非美国公民不得进入美国境内。\n" +
                    "\n" +
                    "        通知并称，在评估了美国往返北京、成都、香港和上海的航班运营情况后，美联航决定暂停这些航班，直至4月24日。美联航还暂停销售合作伙伴航空公司往返美国和中国的前程万里 (MileagePlus)奖励旅行和航班的机票，直至4月24日为止。\n" +
                    "\n" +
                    "        通知指出，为向乘客提供灵活服务，美联航将对前往或来自特定亚洲城市的旅程免除改签费用或允许退款，并建议所有国际旅客在航班起飞前预留额外的机场候机时间。").apply {
                font = Font.font(18.0)
                lineSpacing = 50.0
            }, ImageView(Image("https://himg2.huanqiucdn.cn/attachment2010/2020/0214/20200214072741893.jpg", true)))
        }.apply {
            padding = Insets(20.0)
        })
    }
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(TextFlowDemo::class.java)
        }
    }
}