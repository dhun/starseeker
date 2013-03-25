#!/bin/bash

function INSTALL_LIB_JAR() {
	FILE=$1
	GROUP_ID=$2
	ARTIFACT_ID=$3
	VERSION=$4
	mvn install:install-file -Dfile="$FILE" \
							 -DgroupId="$GROUP_ID" \
							 -DartifactId="$ARTIFACT_ID" \
							 -Dversion="$VERSION" \
							 -Dpackaging=jar

	if [ $? -ne 0 ]; then
		echo $ARTIFACT_IDのインストールに失敗しました
		exit 9
	fi
}



pushd `dirname $0`

INSTALL_LIB_JAR "libs\robolectric-0.9.4-all.jar" "com.pivotallabs" "robolectric" "0.9.4"
INSTALL_LIB_JAR "libs\maps-10.jar" "com.google.android.maps" "maps" "10"

popd

echo 関連ライブラリのインストールに成功しました
