# -*- encoding: utf-8 -*-
from fastapi import FastAPI, Depends, Path, HTTPException
from api_model import \
    student_register_prepare_classroom_model, \
    student_search_model, class_normal_schedule_model, \
    register_student_body, teacher_search_model
from fastapi.middleware.cors import CORSMiddleware
import handle_db
from typing import List

# https://qiita.com/koralle/items/93b094ddb6d3af917702
# https://qiita.com/tomokitamaki/items/de1d74360e05b540c703

app = FastAPI()

origins = ["*"]

app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# uvicorn main:app --reload


@app.get(path="/", tags=['root'])
async def FastAPI():
    return {"message": "Hello World"}


@app.get(path="/student/search", response_model=List[student_search_model], tags=['生徒関連'])
# async def student_search(classroomId: int, studentName: str):
async def 生徒検索(classroomId: int, studentName: str):
    result = [{"studentId": 1,
               "studentName": "StudentName",
               "classroomName": "ClassroomName",
               "prefectureName": "PrefectureName"}]
    return result


@app.get(path="/student/register/prepare-classroom", response_model=List[student_register_prepare_classroom_model], tags=['生徒関連'])
# async def student_register_prepare_classroom():
async def 生徒登録_候補の教室一覧取得():
    result = [
        {"classroomId": 1,
         "classroomName": "ClassroomName",
         "prefectureName": "PrefectureName"}
    ]
    return result


@app.post(path="/student/register", tags=['生徒関連'])
async def 生徒登録(body: register_student_body):
    result = handle_db.create_user()
    if result == 1:
        raise HTTPException(status_code=404, detail="Query Error!!")
    return {
        "status": "OK",
        "data": result
    }


@app.get(path="/teacher/search", response_model=List[teacher_search_model], tags=['講師関連'])
# async def student_register_prepare_classroom():
async def 講師検索(classroomId: int, teacherName: str):
    result = [
        {
            "teacherId": 1,
            "teacherName": "studentName",
        }
    ]
    return result


@app.get(path="/class-schedule", response_model=List[class_normal_schedule_model], tags=['授業予定'])
# async def student_register_prepare_classroom():
async def 通常授業予定取得(targetDate: str):
    result = [
        {"id": 1,
         "period": "6",
         "grade": "grade",
         "subject": "subject",
         "studentId": 1,
         "studentName": "studentName",
         "lecturerName": "lecturerName"}
    ]
    return result


@app.post(path="/api/users", tags=['student'])
async def post_user(user_name: str, user_mail: str):
    result = handle_db.create_user(user_name, user_mail)
    if result == 1:
        raise HTTPException(status_code=404, detail="Query Error!!")
    return {
        "status": "OK",
        "data": result
    }
