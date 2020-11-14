package kr.co.ilg.activity.mypage;

public class HopeJobCareerLVItem {
    private String job;
    private String career;

    public HopeJobCareerLVItem(String job, String career) {
        this.job = job;
        this.career = career;
    }

    public void setMyJob(String job) {
        this.job = job;
    }
    public void setMyCareer(String career) {
        this.career = career;
    }

    public String getMyJob() {
        return this.job;
    }
    public String getMyCareer() {
        return this.career;
    }
}