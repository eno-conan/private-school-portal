# -*- encoding: utf-8 -*-
from fastapi import FastAPI, Depends, Path, HTTPException
import models
from fastapi.middleware.cors import CORSMiddleware
import handle_db
import datetime
from pydantic import BaseModel
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

# base
@app.get(path="/",tags=['base'])
async def FastAPI():
    return {"message": "Hello World"}

# student search
class student_search_model(BaseModel):
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

@app.get(path="/student/search", response_model=List[student_search_model],tags=['student'])
async def get_list_user():
    result = [{"studentId": 1,
               "studentName": "StudentName",
               "classroomName": "ClassroomName",
               "prefectureName": "PrefectureName"}]
    return result

@app.post(path="/api/users",tags=['student'])
async def post_user(user_name: str, user_mail: str):
    result = handle_db.create_user(user_name, user_mail)
    if result == 1:
        raise HTTPException(status_code=404, detail="Query Error!!")
    return {
        "status": "OK",
        "data": result
    }
