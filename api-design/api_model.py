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