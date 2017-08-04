# assign
中期答辩分组程序
##需求
每位导师名下有若干名学生
中期答辩时要求将导师、学生分组
每组中有5位导师，8名学生
且每名学生所在的组里必须有自己的导师
另外，每n个组为一个时间单元（教师不冲突的n个组同时答辩，分在n个房间）
要求输入导师-学生对应的excel表格
输出分好的组
最后导出成excel表格
##实现思路
先建立每位导师-若干名学生的映射表
再对老师进行分组：导师名字-在这一组中所带的学生人数
接着对分好的组进行划分，每n个组在同一个时间单元
然后用学生名字填充学生人数
最后输出分好的组、导出
###注：算法实现出来的是一个可用的划分，而不是最优划分
