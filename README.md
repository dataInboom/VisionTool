# VisionTool
基于视觉效果提取网页新闻内容，中文新闻网页识别率高达97%


demo.java是测试的入口
jdk版本必须是 11
使用的 chrome与chromedriver.exe 的最新版本


titleconfig.txt是收集了5万篇新闻内容后统计的所有标题节点位置的信息叠加得到的一个统计信息

Nitrogen.js是注入到页面的js，通过js获取页面在加载完毕后每个节点的坐标，字体大小，通过json结构返回到java中，当然，也可以通过java中的 selenium 去获取这些信息

{"path":"/HTML/BODY/DIV[6]/DIV[1]/DIV[1]/DIV[2]/P[1]","txt":"xxxxxxxxxxxxxxxx","tagname":"p","deep":23,"textnum":38,"x":378.4375,"width":758.125,
"y":581.59375,"childrenodeNum":0,"fontSize":"18px","fontWeight":"","height":36}

这个是每个节点的基础信息结构



