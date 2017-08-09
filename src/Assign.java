import java.util.ArrayList;

/**
 * Created by malefikus on 29/07/17.
 * 先按照制定的几个规则分组
 */

class Assign {

    //判断是否有重复老师
    private static boolean Repeat(ArrayList<ArrayList<Object>> teacher,
                                  ArrayList<ArrayList<Object>> vect, int num){
        boolean flag = true;
        for (ArrayList<Object> aTeacher : teacher) {
            if (aTeacher.get(0) == vect.get(num).get(0)) {
                flag = false;
            }
        }
        return flag;
    }

    //轮空,总次数少于6的老师
    private static int Zero(ArrayList<ArrayList<Object>> teacher,
                            ArrayList<ArrayList<Object>> vect, int counter){
        for (int k = 0; k < vect.size(); k++){
            if ((int)vect.get(k).get(5)<6){
                //防止同一组出现同一个老师
                if (Repeat(teacher, vect, k)){
                    //教师信息加入分组
                    ArrayList<Object> stunum = new ArrayList<>();
                    stunum.add(vect.get(k).get(0));
                    stunum.add(0);
                    teacher.add(stunum);
                    //更新总次数
                    vect.get(k).set(5, ((int)vect.get(k).get(5)+1));
                    counter--;
                    break;
                }
            }
        }
        return counter;
    }

    //1或余数为1
    private static boolean One(ArrayList<ArrayList<Object>> teacher,
                               ArrayList<ArrayList<Object>> vect, boolean flag_has1){
        for (int j = 0; j<vect.size(); j++){
            //每次只带一个学生的老师
            ArrayList<Object> stunum;
            if ((int)vect.get(j).get(2)>0&&
                    (int)vect.get(j).get(3)==1){
                //防止同一组出现同一个老师
                if (Repeat(teacher, vect, j)){
                    //教师信息加入分组
                    stunum = new ArrayList<>();
                    stunum.add(vect.get(j).get(0));
                    stunum.add(1);
                    teacher.add(stunum);
                    //更新总人数、剩余次数
                    vect.get(j).set(1, ((int)vect.get(j).get(1)-1));
                    vect.get(j).set(2, ((int)vect.get(j).get(2)-1));
                    flag_has1 = true;
                    break;
                }
            }
            //余数为1的老师
            else if ((int)vect.get(j).get(4)==1){
                //防止同一组出现同一个老师
                if (Repeat(teacher, vect, j)){
                    //教师信息加入分组
                    stunum = new ArrayList<>();
                    stunum.add(vect.get(j).get(0));
                    stunum.add(1);
                    teacher.add(stunum);
                    //更新总人数、余数
                    vect.get(j).set(1, ((int)vect.get(j).get(1)-1));
                    vect.get(j).set(4, ((int)vect.get(j).get(4)-1));
                    flag_has1 = true;
                    break;
                }
            }
        }
        return flag_has1;
    }

    //2或余数为2
    private static boolean Two(ArrayList<ArrayList<Object>> teacher,
                               ArrayList<ArrayList<Object>> vect, boolean flag_has2){
        for (int j = 0; j<vect.size(); j++){
            //每次带2个学生的老师
            ArrayList<Object> stunum;
            if ((int)vect.get(j).get(2)>0&&
                    (int)vect.get(j).get(3)==2){
                //防止同一组出现同一个老师
                if (Repeat(teacher, vect, j)){
                    //教师信息加入分组
                    stunum = new ArrayList<>();
                    stunum.add(vect.get(j).get(0));
                    stunum.add(2);
                    teacher.add(stunum);
                    //更新总人数、剩余次数
                    vect.get(j).set(1, ((int)vect.get(j).get(1)-2));
                    vect.get(j).set(2, ((int)vect.get(j).get(2)-1));
                    flag_has2 = true;
                    break;
                }
            }
            //余数为2的老师
            else if ((int)vect.get(j).get(4)==2){
                //防止同一组出现同一个老师
                if (Repeat(teacher, vect, j)){
                    //教师信息加入分组
                    stunum = new ArrayList<>();
                    stunum.add(vect.get(j).get(0));
                    stunum.add(2);
                    teacher.add(stunum);
                    //更新总人数、余数
                    vect.get(j).set(1, ((int)vect.get(j).get(1)-2));
                    vect.get(j).set(4, ((int)vect.get(j).get(4)-2));
                    flag_has2 = true;
                    break;
                }
            }
        }
        return flag_has2;
    }

    //还剩多少次每组学生人数为n，作为循环条件
    private static int Count3(ArrayList<ArrayList<Object>> vect, int n){
        int count = 0;
        for (ArrayList<Object> aVect : vect) {
            //如果每组人数为n
            if ((int) aVect.get(3) == n) {
                //加次数
                count += (int) aVect.get(2);
            }
        }
        return count;
    }

    //还剩多少次余数为n，作为循环条件
    private static int Count4(ArrayList<ArrayList<Object>> vect, int n){
        int count = 0;
        for (ArrayList<Object> aVect : vect) {
            //如果余数为n
            if ((int) aVect.get(4) == n) {
                //加一次
                count++;
            }
        }
        return count;
    }

    // 按照规则，先给老师分组，每组中每个老师带多少个学生
    static ArrayList<ArrayList<ArrayList<Object>>> AssignNum(){
        ArrayList<ArrayList<Object>> teachervect = Fileio.Num();
        ArrayList<ArrayList<ArrayList<Object>>> assignGroup = new ArrayList<>();
        ArrayList<ArrayList<Object>> teachers;
        ArrayList<Object> stunum;

        //teachervect：0教师名，1剩余总学生人数，2剩余次数，3每组学生人数，4余数，5总次数（用于判断轮空）
        for (ArrayList<Object> aTeachervect : teachervect) {
            //获取不包含余数的总次数
            int tmp = (int) aTeachervect.get(2);
            //如果余数大于0，增加一次
            if ((int) aTeachervect.get(4) > 0) {
                tmp++;
            }
            //将包括余数的总次数添加到5
            aTeachervect.add(tmp);
        }

        //先把4学生的排完
        int count4 = Count3(teachervect, 4);
        while (count4 > 0){
            //4+1+1+1+1
            for (int i = 0; i<teachervect.size(); i++){
                //带4个学生的教师
                if ((int)teachervect.get(i).get(2)>0 && (int)teachervect.get(i).get(3) == 4){
                    //教师信息加入分组
                    teachers = new ArrayList<>();
                    stunum = new ArrayList<>();
                    stunum.add(teachervect.get(i).get(0));
                    stunum.add(4);
                    teachers.add(stunum);
                    //更新总人数、剩余次数
                    teachervect.get(i).set(1, ((int)teachervect.get(i).get(1)-4));
                    teachervect.get(i).set(2, ((int)teachervect.get(i).get(2)-1));

                    //带1个学生或余数为1的教师，共需要4位
                    int counter = 4;
                    while (counter > 0){
                        boolean flag_has1 = false;
                        flag_has1 = One(teachers, teachervect, flag_has1);
                        if (flag_has1){
                            counter--;
                        }
                        //找不到余数为1的老师，用总次数少的老师轮空
                        else{
                            counter = Zero(teachers, teachervect, counter);
                        }
                    }

                    //将分成4+1+1+1+1的组加入分组列表
                    assignGroup.add(teachers);
                    break;
                }
            }

            //更新count4
            count4 = Count3(teachervect, 4);
        }

        //再把余数为3学生的排完
        int count3 = Count4(teachervect, 3);
        while (count3 > 0){
            //3+2+1+1+1
            for (int i = 0; i<teachervect.size(); i++){
                //余数为3的教师
                if ((int)teachervect.get(i).get(4)==3){
                    //教师信息加入分组
                    teachers = new ArrayList<>();
                    stunum = new ArrayList<>();
                    stunum.add(teachervect.get(i).get(0));
                    stunum.add(3);
                    teachers.add(stunum);
                    //更新总人数、余数
                    teachervect.get(i).set(1, ((int)teachervect.get(i).get(1)-3));
                    teachervect.get(i).set(4, ((int)teachervect.get(i).get(4)-3));

                    //带2个学生或余数为2的教师，共需要1位
                    boolean flag_has2 = false;
                    flag_has2 = Two(teachers, teachervect, flag_has2);
                    //没有2学生的老师，用1学生的代替
                    if (!flag_has2){
                        boolean flag_has1 = false;
                        flag_has1 = One(teachers, teachervect, flag_has1);
                        //没有1学生的老师，用轮空老师代替
                        if (!flag_has1){
                            Zero(teachers, teachervect, 1);
                        }
                    }

                    //带1个学生或余数为1的老师，共需要3位
                    int counter = 3;
                    while (counter > 0){
                        boolean flag_has1 = false;
                        flag_has1 = One(teachers, teachervect, flag_has1);
                        if (flag_has1){
                            counter--;
                        }
                        //找不到余数为1的老师，用总次数少的老师轮空
                        else{
                            counter = Zero(teachers, teachervect, counter);
                        }
                    }

                    //将分成3+2+1+1+1的组加入列表
                    assignGroup.add(teachers);
                    break;
                }
            }

            //更新count3
            count3 = Count4(teachervect, 3);
        }

        //把剩余的2学生或余数为2学生的排完
        int count2 = Count3(teachervect, 2) + Count4(teachervect, 2);
        while (count2 > 0){
            //2+2+2+1+1
            for (int i = 0; i<teachervect.size(); i++){
                //带2个学生的老师
                if ((int)teachervect.get(i).get(2)>0 && (int)teachervect.get(i).get(3) == 2){
                    //教师信息加入分组
                    teachers = new ArrayList<>();
                    stunum = new ArrayList<>();
                    stunum.add(teachervect.get(i).get(0));
                    stunum.add(2);
                    teachers.add(stunum);
                    //更新总人数、剩余次数
                    teachervect.get(i).set(1, ((int)teachervect.get(i).get(1)-2));
                    teachervect.get(i).set(2, ((int)teachervect.get(i).get(2)-1));

                    //带2个学生或余数为2的老师，还需要2位
                    int counter = 2;
                    while (counter > 0){
                        boolean flag_has2 = false;
                        flag_has2 = Two(teachers, teachervect, flag_has2);
                        if (flag_has2){
                            counter--;
                        }
                        //找不到2的，用1代替
                        else if (One(teachers, teachervect, flag_has2)){
                            counter--;
                        }
                        //找不到1的，轮空
                        else{
                            counter = Zero(teachers, teachervect, counter);
                        }
                    }

                    //带1个学生或余数为1的老师，还需要2位
                    counter = 2;
                    while (counter > 0){
                        boolean flag_has1 = false;
                        flag_has1 = One(teachers, teachervect, flag_has1);
                        if (flag_has1){
                            counter--;
                        }
                        //找不到余数为1的老师，用总次数少的老师轮空
                        else{
                            counter = Zero(teachers, teachervect, counter);
                        }
                    }

                    //将分成2+2+2+1+1的组加入列表
                    assignGroup.add(teachers);
                    break;
                }
                //余数为2的老师
                else if ((int)teachervect.get(i).get(4)==2){
                    //教师信息加入分组
                    teachers = new ArrayList<>();
                    stunum = new ArrayList<>();
                    stunum.add(teachervect.get(i).get(0));
                    stunum.add(2);
                    teachers.add(stunum);
                    //更新总人数、余数
                    teachervect.get(i).set(1, ((int)teachervect.get(i).get(1)-2));
                    teachervect.get(i).set(4, ((int)teachervect.get(i).get(4)-2));

                    //带2个学生或余数为2的老师，还需要2位
                    int counter = 2;
                    while (counter > 0){
                        boolean flag_has2 = false;
                        flag_has2 = Two(teachers, teachervect, flag_has2);
                        if (flag_has2){
                            counter--;
                        }
                        //找不到2的，用1代替
                        else if (One(teachers, teachervect, flag_has2)){
                            counter--;
                        }
                        //找不到1的，轮空
                        else{
                            counter = Zero(teachers, teachervect, counter);
                        }
                    }

                    //带1个学生或余数为1的老师，还需要2位
                    counter = 2;
                    while (counter > 0){
                        boolean flag_has1 = false;
                        flag_has1 = One(teachers, teachervect, flag_has1);
                        if (flag_has1){
                            counter--;
                        }
                        //找不到余数为1的老师，用总次数少的老师轮空
                        else{
                            counter = Zero(teachers, teachervect, counter);
                        }
                    }

                    //将分成2+2+2+1+1的组加入列表
                    assignGroup.add(teachers);
                    break;
                }
            }

            //更新count2
            count2 = Count3(teachervect, 2) + Count4(teachervect, 2);
        }

        //最后把剩余的1学生或余数为1学生的排完
        int count1 = Count3(teachervect, 1) + Count4(teachervect, 1);
        while (count1 > 0){
            //如果有1剩余，1+1+1+1+1
            for (int i = 0; i<teachervect.size(); i++){
                //带1个学生的老师
                if ((int)teachervect.get(i).get(2)>0 && (int)teachervect.get(i).get(3) == 1){
                    //教师信息加入分组
                    teachers = new ArrayList<>();
                    stunum = new ArrayList<>();
                    stunum.add(teachervect.get(i).get(0));
                    stunum.add(1);
                    teachers.add(stunum);
                    //更新总人数、剩余次数
                    teachervect.get(i).set(1, ((int)teachervect.get(i).get(1)-1));
                    teachervect.get(i).set(2, ((int)teachervect.get(i).get(2)-1));

                    //带1个学生或余数为1的老师，还需要4位
                    int counter = 4;
                    while (counter > 0){
                        boolean flag_has1 = false;
                        flag_has1 = One(teachers, teachervect, flag_has1);
                        if (flag_has1){
                            counter--;
                        }
                        //找不到余数为1的老师，用总次数少的老师轮空
                        else{
                            counter = Zero(teachers, teachervect, counter);
                        }
                    }

                    //将分成1+1+1+1+1的组加入列表
                    assignGroup.add(teachers);
                    break;
                }
                //余数为1的老师
                else if ((int)teachervect.get(i).get(4)==1){
                    //教师信息加入分组
                    teachers = new ArrayList<>();
                    stunum = new ArrayList<>();
                    stunum.add(teachervect.get(i).get(0));
                    stunum.add(1);
                    teachers.add(stunum);
                    //更新总人数、余数
                    teachervect.get(i).set(1, ((int)teachervect.get(i).get(1)-1));
                    teachervect.get(i).set(4, ((int)teachervect.get(i).get(4)-1));

                    //带1个学生或余数为1的老师，还需要4位
                    int counter = 4;
                    while (counter > 0){
                        boolean flag_has1 = false;
                        flag_has1 = One(teachers, teachervect, flag_has1);
                        if (flag_has1){
                            counter--;
                        }
                        //找不到余数为1的老师，用总次数少的老师轮空
                        else{
                            counter = Zero(teachers, teachervect, counter);
                        }
                    }

                    //将分成1+1+1+1+1的组加入列表
                    assignGroup.add(teachers);
                    break;
                }
            }

            //更新count1
            count1 = Count3(teachervect, 1) + Count4(teachervect, 1);
        }

//        int totalstu = 0;
//        for (ArrayList<Object> aTeachervect : teachervect) {
//            totalstu += (int) aTeachervect.get(1);
//        }
//        System.out.println(totalstu);

        return assignGroup;
    }

}