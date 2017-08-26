
# SketchPad Principle：

Notice : Pathview not have draw function ，PointPath have

![](https://github.com/ShaunSheep/ScaleSketchPadDemo/blob/master/docs/pathviewdoc.png)


# Basis ：


 <img src="drawpath_meitu_1.jpg" width = "640" height = "340" alt="draw path" align=center id="rotate "/>

       one touch entent log：
      action test: finger down
      action test: finger move
      action test: finger move
      action test: finger move
      action test: finger move
      action test: finger move
      action test: finger move
      action test: finger move
      action test: finger up


# Design


![](https://github.com/ShaunSheep/ScaleSketchPadDemo/blob/master/docs/uml.png)

PointPath have drawn function：
1. save point state
2. receive  Canvas to draw

PathView controller the PointPath
1. receive Touch evnent and respond to do sth
2. save Path which can undo or invalidte
3. onDraw()  

