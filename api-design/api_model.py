from pydantic import BaseModel


class student_search_model(BaseModel):
    # student search
    studentId: int
    studentName: str
    classroomName: str
    prefectureName: str

    class Config:
        schema_extra = {
            "example": {
                "studentId": 1,
                "studentName": "StudentName",
                "classroomName": "ClassroomName",
                "prefectureName": "PrefectureName"
            }
        }


class student_register_prepare_classroom_model(BaseModel):
    classroomId: int
    prefectureName: str
    classroomName: str

    class Config:
        schema_extra = {
            "example": {
                "classroomId": 1,
                "prefectureName": "北海道",
                "classroomName": "札幌"
            }
        }


class register_student_body(BaseModel):
    studentName: str
    birthDay: str
    grade: str
    classroomId: int

    class Config:
        schema_extra = {
            "example": {
                "studentName": "a",
                "birthDay": "2006-12-02",
                "grade": "h1",
                "classroomId": 1
            }
        }

class teacher_search_model(BaseModel):
    # teacher search
    teacherId: int
    teacherName: str

    class Config:
        schema_extra = {
            "example": {
                "teacherId": 1,
                "teacherName": "teacherName",
            }
        }



class class_normal_schedule_model(BaseModel):
    id: int
    period: str
    grade: str
    subject: str
    studentId: int
    studentName: str
    lecturerName: str

    class Config:
        schema_extra = {
            "example": {
                "id": 1,
                "period": "6",
                "grade": "高校1年生",
                "subject": "算数",
                "studentId": 1,
                "studentName": "a",
                "lecturerName": "講師A"
            }
        }
