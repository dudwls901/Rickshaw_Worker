package kr.co.ilg.activity.findwork;

public class ListViewItem {

    public String title,date,job,place,business_reg_num,office,paid,jp_job_start_time, jp_job_finish_time, jp_contents, jp_num, fieldname, fieldcode;
    public int pay, current_people, total_people;
    public boolean urgency ;
    // 현장 정보의 일자리 정보
    public ListViewItem(String title,String date,int pay,String job,String place,String office,int current_people,int total_people,
                        String jp_job_start_time, String jp_job_finish_time, String jp_contents, String field_name){
        this.title=title;
        this.date=date;
        this.pay=pay;
        this.job=job;
        this.place=place;
        this.office=office;
        this.current_people=current_people;
        this.total_people=total_people;
        this.jp_job_start_time = jp_job_start_time;
        this.jp_job_finish_time = jp_job_finish_time;
        this.jp_contents = jp_contents;
        this.fieldname = field_name;
    }
/*
    // Main
    public ListViewItem(String title,String date,int pay,String job,String place,String office,int current_people,int total_people, boolean urgency, String jp_job_start_time, String jp_job_finish_time, String jp_contents){
        this.title=title;
        this.date=date;
        this.pay=pay;
        this.job=job;
        this.place=place;
        this.office=office;
        this.current_people=current_people;
        this.total_people=total_people;
        this.urgency =urgency;
        this.jp_job_start_time = jp_job_start_time;
        this.jp_job_finish_time = jp_job_finish_time;
        this.jp_contents = jp_contents;
    }
*/
    // Main, 지원현황 선발대기
    public ListViewItem(String business_reg_num, String jp_num, String title,String date,int pay,String job,String place,String office,int current_people,int total_people, boolean urgency, String jp_job_start_time, String jp_job_finish_time, String jp_contents, String fieldname){
        this.business_reg_num = business_reg_num;
        this.title=title;
        this.date=date;
        this.pay=pay;
        this.job=job;
        this.place=place;
        this.office=office;
        this.current_people=current_people;
        this.total_people=total_people;
        this.urgency =urgency;
        this.jp_job_start_time = jp_job_start_time;
        this.jp_job_finish_time = jp_job_finish_time;
        this.jp_contents = jp_contents;
        this.jp_num = jp_num;
        this.fieldname = fieldname;
    }

    // 지원현황 선발완료
    public ListViewItem(String jp_num, String title,String date,int pay,String job,String place,String office,int current_people,int total_people, boolean urgency, String jp_job_start_time, String jp_job_finish_time, String jp_contents){
        this.title=title;
        this.date=date;
        this.pay=pay;
        this.job=job;
        this.place=place;
        this.office=office;
        this.current_people=current_people;
        this.total_people=total_people;
        this.urgency =urgency;
        this.jp_job_start_time = jp_job_start_time;
        this.jp_job_finish_time = jp_job_finish_time;
        this.jp_contents = jp_contents;
        this.jp_num = jp_num;
    }
    //나의현장탭 지난현장 +paid
    public ListViewItem(String business_reg_num,String jp_num, String fieldname,String title,String date,int pay,String job,String place,String office, String paid, boolean urgency,String jp_job_start_time, String jp_job_finish_time, String jp_contents, int total_people){
        this.title=title;
        this.date=date;
        this.business_reg_num = business_reg_num;
        this.pay=pay;
        this.job=job;
        this.fieldname = fieldname;
        this.place=place;
        this.office=office;
        this.paid=paid;
        this.jp_num = jp_num;
        this.urgency=urgency;
        this.jp_job_start_time = jp_job_start_time;
        this.jp_job_finish_time = jp_job_finish_time;
        this.jp_contents = jp_contents;
        this.total_people=total_people;
    }

/*
    //나의현장탭 지난현장 +paid
    public ListViewItem(String title,String date,int pay,String job,String place,String office, String paid, boolean urgency,String jp_job_start_time, String jp_job_finish_time, String jp_contents){
        this.title=title;
        this.date=date;
        this.pay=pay;
        this.job=job;
        this.place=place;
        this.office=office;
        this.paid=paid;
        this.urgency=urgency;
        this.jp_job_start_time = jp_job_start_time;
        this.jp_job_finish_time = jp_job_finish_time;
        this.jp_contents = jp_contents;
    }
*/
    public ListViewItem(String title,String date,int pay,String job,String place,String office, String paid, boolean urgency){
        this.title=title;
        this.date=date;
        this.pay=pay;
        this.job=job;
        this.place=place;
        this.office=office;
        this.paid=paid;
        this.urgency=urgency;
    }
    public ListViewItem(String title,String date,int pay,String job,String place,String office, boolean urgency){
        this.title=title;
        this.date=date;
        this.pay=pay;
        this.job=job;
        this.place=place;
        this.office=office;
        this.urgency=urgency;
    }
}
