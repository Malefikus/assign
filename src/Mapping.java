import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Mapping {
    static ArrayList<ArrayList<ArrayList<ArrayList<Object>>>> mapStu(){
        ArrayList<ArrayList<ArrayList<ArrayList<Object>>>> mapStu = new ArrayList<>();
        ArrayList<ArrayList<ArrayList<ArrayList<Object>>>> assignRoom = Room.assignRoom();
        Map<String, List<String>> stuMap = Fileio.Group();

        for (ArrayList<ArrayList<ArrayList<Object>>> group : assignRoom){
            //对于assignRoom中的每个组，为mapStu新建条目
            ArrayList<ArrayList<ArrayList<Object>>> unit = new ArrayList<>();
            for (ArrayList<ArrayList<Object>> room : group){
                //对于assignRoom中每个组的每个房间，为mapStu新建条目
                ArrayList<ArrayList<Object>> names = new ArrayList<>();
                ArrayList<Object> teachers = new ArrayList<>();
                ArrayList<Object> students = new ArrayList<>();
                for (ArrayList<Object> teachernum : room){
                    //将老师姓名加入teachers
                    String teachername = (String) teachernum.get(0);
                    teachers.add(teachername);
                    //逐个将学生姓名加入students
                    for (int i = 0; i < (int)teachernum.get(1); i++){
                        //加入学生姓名
                        String stuname = stuMap.get(teachername).get(0);
                        students.add(stuname);
                        //从map里删除已被加入的学生姓名
                        List<String> stulist = stuMap.get(teachername);
                        stulist.remove(stuname);
                        stuMap.put(teachername, stulist);
                    }
                }
                names.add(teachers);
                names.add(students);
                unit.add(names);
            }
            mapStu.add(unit);
        }
        return mapStu;
    }
}
