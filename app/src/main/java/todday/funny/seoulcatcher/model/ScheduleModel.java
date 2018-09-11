package todday.funny.seoulcatcher.model;

public class ScheduleModel {
    private String date;

    public ScheduleModel(){}

    public ScheduleModel(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
