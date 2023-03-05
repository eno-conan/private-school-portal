# -*- encoding: utf-8 -*-
from fastapi import FastAPI, Depends, Path, HTTPException
from api_model import student_register_prepare_classroom_model, student_search_model
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

@app.get(path="/", tags=['base'])
async def FastAPI():
    return {"message": "Hello World"}

@app.get(path="/student/search", response_model=List[student_search_model], tags=['student'])
async def student_search():
    result = [{"studentId": 1,
               "studentName": "StudentName",
               "classroomName": "ClassroomName",
               "prefectureName": "PrefectureName"}]
    return result

@app.get(path="/student/register/prepare-classroom", response_model=List[student_register_prepare_classroom_model], tags=['student'])
async def student_register_prepare_classroom():
    result = [
        {"classroomId": 1,
         "classroomName": "ClassroomName",
         "prefectureName": "PrefectureName"}
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
