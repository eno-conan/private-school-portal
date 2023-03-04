- 起動コマンド
```py
uvicorn main:app --reload
```
json to yaml
https://lazesoftware.com/ja/tool/jsonyaml/

- 土台作成に使用
  - https://qiita.com/kim-grs/items/3debef3841c78f03c8c0
  - https://self-methods.com/fastapi-create-database/

- テーブル設計
  - https://zenn.dev/shimakaze_soft/articles/6e5e47851459f5

- Docker(FastAPI)
  - https://zenn.dev/tomofyro/articles/3b94e5ce17bd6e

- openapi-generator
  - https://fastapi.tiangolo.com/advanced/generate-clients/#openapi-client-generators
  - https://zenn.dev/offers/articles/20220620-openapi-generator

- 環境変数読み込み
  - https://maku77.github.io/python/env/dotenv.html

### MYSQL関連
- Docker環境構築
  - https://zenn.dev/re24_1986/articles/153cdc5db96dc0
- \MySQLワークベンチでupdate文を実行すると1175のエラーが発生(恒久対応済)
  - https://yoneyore.hatenablog.com/entry/2016/01/05/200047
- ユーザ作成
  - https://www.digitalocean.com/community/tutorials/how-to-create-a-new-user-and-grant-permissions-in-mysql-ja
- ユーザー操作
  - https://www.javadrive.jp/mysql/user/index11.html
- コンテナ入る
    `docker exec -it mysql-container bash`

- 以下警告
  - `MySQL is started in --skip-name-resolve mode; you must restart it without this switch for this grant to work`
  - https://tech-blog.s-yoshiki.com/entry/56

- コンテナのIPを知る方法（コンテナ再起動したらどうするのか？）
  - https://leck-tech.com/docker/docker-host-ip