JUNITでstarseeker-testを実行する場合には、次の手順をふんでください。
1. プロジェクト参照を追加
    1. starseeker-testのプロパティを開く
    2. starseekerプロジェクトの参照を追加
           java-buildpath -> project から starseeker を追加
    3. JUNITの構成を変更
           実行構成 -> 引数 -> 作業ディレクトリ= その他, value=${workspace_loc:starseeker}
    4. JUNITの実行
