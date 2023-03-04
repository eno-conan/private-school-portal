from sqlalchemy import create_engine
import sys
from sqlalchemy.orm import (sessionmaker, relationship, scoped_session)
import os
from dotenv import load_dotenv
load_dotenv()

sys.dont_write_bytecode = True

#setting db connection
# localPC
USER = os.getenv('DB_USER_LOCAL')
PASSWORD = os.getenv('DB_PASS_LOCAL')
PORT = os.getenv('DB_PORT_LOCAL')
url = F'mysql+pymysql://{USER}:{PASSWORD}@localhost:{PORT}/try_prisma?charset=utf8'
# Docker
# url = "mysql+pymysql://{USER}:{PASSWORD}@127.0.0.1:{PORT}/demo?charset=utf8"
engine = create_engine(url, echo=False, pool_recycle=10)

#create session
def create_new_session():
    return  scoped_session(sessionmaker(autocommit=False, autoflush=True, expire_on_commit=False, bind=engine))