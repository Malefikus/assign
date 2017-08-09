import java.util.ArrayList;

public class OutputFormat {

    public static ArrayList<ArrayList<Object>> outputFormat(){
        ArrayList<ArrayList<ArrayList<ArrayList<Object>>>> stuMap = Mapping.mapStu();
        ArrayList<ArrayList<Object>> output = new ArrayList<>();

        for (ArrayList<ArrayList<ArrayList<Object>>> unit : stuMap){
            //给每个答辩单元加上单元号
            ArrayList<ArrayList<Object>> StuMap = new ArrayList<>();
            ArrayList<Object> Unit = new ArrayList<>();
            int unitIndex = stuMap.indexOf(unit) + 1;
            Unit.add("Unit " + unitIndex);
            StuMap.add(Unit);
            unit.add(0, StuMap);

            for (ArrayList<ArrayList<Object>> room : unit){
                //给每个答辩房间加上房间号，注意不要把前面添加的单元号也算作一个房间
                if (room.size() > 1){
                    ArrayList<Object> Room = new ArrayList<>();
                    int roomIndex = unit.indexOf(room);
                    Room.add("Room " + roomIndex);
                    room.add(0, Room);
                }

                for (ArrayList<Object> namelist : room){
                    output.add(namelist);
                }
            }
        }

        return output;
    }

}
