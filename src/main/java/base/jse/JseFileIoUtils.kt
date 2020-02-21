package base.jse

import org.apache.commons.io.FileUtils
import org.apache.commons.lang.ClassUtils
import org.apache.commons.lang.StringUtils
import org.apache.commons.lang.SystemUtils
import org.apache.commons.lang.Validate
import java.io.*
import java.math.BigDecimal
import java.util.*
import kotlin.math.ceil

/**
 * File的重要信息
 * 1.获取名称 name
 * 2.获取路径 path
 * 3.获取绝对路径 absolutePath
 * 4.获取父路径 parent
 * 5.获取父目录 parentFile
 * 6.判断File是否存在 exists
 * 7.判断File是否是文件 isFile
 * 8.判断File是否是目录 isDirectory
 * 9.获取File长度，以byte为单位 length
 * 10.获取Filed的所有子文件或子目录名称 list
 * 11.获取File的所有子文件或子目录对象 listFiles()
 * 12.获取计算机的所有根盘符名称 listRoots(静态)
 *
 * File的重要方法
 * 1.创建新文件 createNewFile
 * 2.删除文件 delete deleteOnExits
 * 3.创建目录，上级目录必须存在 mkdir
 * 4.创建目录以及可能不存在的上级目录 mkdirs
 *
 * File的扩展属性(Kotlin)
 * 1.获取文件的扩展名 extension
 * 2.判断文件路径是绝对路径还是相对路径 isRooted
 * 3.返回不带扩展名的文件名 nameWithoutExtension
 * 4.将文件路径的分隔符修改为“/” invariantSeparatorsPath
 *
 * File的扩展方法(Kotlin)
 * 1.复制文件 copyTo
 * 2.读取文件的字节数组 readBytes
 * 2.写入字节数组到文件 writeBytes
 * 3.向文件添加字节数组 appendBytes
 * 4.读取文件中的文本 readText
 * 5.将文本写入文件 writeText
 * 6.向文件添加文本 appendText
 * 7.遍历文件全部字节 forEachBlock
 * 8.遍历文件的每一行文本 forEachLine
 * 9.读取文本并返回包含每行文字的List readLines
 * 10.复制文件夹的全部内容 copyRecursively
 * 11.删除文件夹的全部内容 deleteRecursively
 * 。。。。。。
 */
interface JseFileIoUtils {

    //获取文件的大小
    val File.KB get() = BigDecimal(length()).divide(BigDecimal(1024)).toDouble()
    val File.MB get() = BigDecimal(length()).divide(BigDecimal(1024 * 1024)).toDouble()
    val File.GB get() = BigDecimal(length()).divide(BigDecimal(1024 * 1024 * 1024)).toDouble()

    /**
     * 写入对象到文件
     */
    fun File.writeObject(obj:Any){
        if(!this.exists()) this.createNewFile()
        val stream = ObjectOutputStream(this.outputStream())
        if(obj is Serializable){
            stream.writeObject(obj)
        } else {
            throw Exception("对象未序列化")
        }
        stream.close()
    }

    /**
     * 从文件读取对象
     */
    fun <T> File.readObject(obj:Any):T{
        if(!this.exists())
            throw Exception("文件不存在")
        val stream = ObjectInputStream(this.inputStream())
        if(obj is Serializable){
            @Suppress("UNCHECKED_CAST")
            val t = stream.readObject() as T
            stream.close()
            return t
        } else {
            throw Exception("对象未序列化")
        }
    }

    /**
     * 打印指定目录下所有文件与目录信息
     */
    fun File?.printAllFileList(deep: Int = 0) {
        for (i in 0 until deep) {
            print("--")
        }
        println(this?.name)
        if (null == this || !this.exists()) {
            return
        } else {
            this.listFiles()?.forEach {
                it.printAllFileList(deep + 2)
            }
        }
    }

    companion object{
        var len = 0L
    }

    enum class ByteUnit{
        B,KB,MB,GB
    }

    /**
     * 获取指定目录下所有文件的长度
     */
    fun File?.getAllFilesLength(unit: ByteUnit = ByteUnit.B): Number {
        len = 0
        this.calcFilesLength()
        return when(unit){
            ByteUnit.GB ->BigDecimal(len).divide(BigDecimal(1024 * 1024 * 1024)).toDouble()
            ByteUnit.MB ->BigDecimal(len).divide(BigDecimal(1024 * 1024)).toDouble()
            ByteUnit.KB ->BigDecimal(len).divide(BigDecimal(1024)).toDouble()
            else-> len
        }
    }

    private fun File?.calcFilesLength(){
        if(this!=null&&this.exists()){
            if(this.isFile){
                len += this.length()
            } else {
                this.listFiles()?.forEach {
                    it.calcFilesLength()
                }
            }
        }
    }

    fun sliceFile(){
        val video = File(FileIoDemo.getUserDesktop(), "sexgame.mp4")
        val raf = RandomAccessFile(video.absolutePath, "rw")
        var length = video.length()
        var readLength = 1024*1024*10 //一个文件为10MB
        val readCount = ceil(length/readLength.toDouble()).toInt()
        for (i in 1 .. readCount){
            if(i != readCount){
                val ba = ByteArray(readLength.toInt())
                raf.read(ba)
                FileUtils.writeByteArrayToFile(File("D:","game$i.mp4"), ba)
                raf.seek(readLength*i.toLong())
                length -= readLength
            } else {
                val ba = ByteArray(length.toInt())
                raf.read(ba)
                FileUtils.writeByteArrayToFile(File("D:","game$i.mp4"), ba)
            }
        }
        raf.close()
    }

    fun mergeFile(){
        val vector = Vector<InputStream>()
        vector.addAll(arrayOf(File("D:","game1.mp4").inputStream(),
                File("D:","game2.mp4").inputStream(),
                File("D:","game3.mp4").inputStream(),
                File("D:","game4.mp4").inputStream(),
                File("D:","game5.mp4").inputStream(),
                File("D:","game6.mp4").inputStream()))
        val fos = File("D:","gameLast.mp4").outputStream()
        val sis = SequenceInputStream(vector.elements())
        val ba = ByteArray(1024)
        var len = sis.read(ba)
        while (len != -1){
            fos.write(ba, 0, len)
            len = sis.read(ba)
        }
        fos.flush()
        sis.close()
        fos.close()
    }




















}