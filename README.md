# Tesseract-OCR

Tesseract-OCR字库训练：

步骤1：

1）打开jTessBoxEditor（我的地址是D:\Tesseract-OCR\Tesseract-OCR\jTessBoxEditorFX），选择Trainer->Tools->Merge TIFF，进入训练样本所在文件夹，选中要参与训练的样本图片

2）点击 “打开” 后弹出保存对话框，选择保存在当前路径下，文件命名为 “ wyuan.test.exp0.tif ” ，格式只有一种 “TIFF” 可选。
注意：tif文面命名格式[lang].[fontname].exp[num].tif
lang是语言，fontname是字体，num为自定义数字。
比如我们要训练自定义字库 wyuan，字体名test，那么我们把图片文件命名为 wyuan.test.exp0.tif
步骤1成功完成后会在该图片所在目录生成一个 wyuan.test.exp0.tif 的tif图片

步骤2：

在上一步骤生成的“ wyuan.test.exp0.tif ”文件所在目录下打开命令行程序，执行下面命令
tesseract wyuan.test.exp0.tif wyuan.test.exp0 -l chi_sim -psm 6 batch.nochop makebox
步骤2成功之后会生成 wyuan.test.exp0.box 文件

步骤3：

打开jTessBoxEditor点击Box Editor ->Open，打开步骤2中生成的“ wyuan.test.exp0.tif ”，会自动关联到“ wyuan.test.exp0.box ”文件
注意：这两文件要求在同一目录下。调整完点击“save”保存修改。

步骤4：

生成.tr训练文件，执行下面命令
tesseract wyuan.test.exp0.tif wyuan.test.exp0 nobatch box.train
执行完之后，会在当前目录生成 wyuan.test.exp0.tr 以及 wyuan.test.exp0.txt 文件。

步骤5：

生成字符集文件，执行下面命令
unicharset_extractor wyuan.test.exp0.box
执行完之后，会在当前目录生成一个名为“unicharset”的文件

步骤6：

1）手动新建 font_properties （没有文件后缀名），用Notepad++打开，里面写“字体 0 0 0 0 0”，以此为例就是“test 0 0 0 0 0”，表示字体test的粗体、倾斜等共计5个属性

2）执行下面命令，生成shape文件
shapeclustering -F font_properties -U unicharset -O wyuan.unicharset wyuan.test.exp0.tr
执行完之后，会生成 shapetable 和 wyuan.unicharset 两个文件。

步骤7：

执行下面命令，生成聚字符特征文件
mftraining -F font_properties -U unicharset -O wyuan.unicharset wyuan.test.exp0.tr
成功会生成 inttemp、pffmtable、shapetable和wyuan.unicharset四个文件

步骤8：

执行下面命令，生成字符正常化特征文件
cntraining wyuan.test.exp0.tr
成功会生成 normproto 文件

步骤9：

重要文件重命令，以便后续步骤以文件前缀来合成字库文件
重新命名inttemp、pffmtable、shapetable和normproto这四个文件的名字为[lang].xxx，以此为例就是 wyuan.inttemp， wyuan.pffmtable， wyuan.shapetable， wyuan.normproto

步骤10：

执行下面命令，合并训练文件
combine_tessdata wyuan.
成功会生成 wyuan.traineddata 文件

步骤11：

将生成的“ wyuan.traineddata ”语言包文件复制到Tesseract-OCR安装目录下的 tessdata 文件夹中
在需要识别的图片所在位置打开cmd，执行以下命令
tesseract 样本图片名 test -l wyuan
成功后会识别结果将会写在一个test.txt文件中
