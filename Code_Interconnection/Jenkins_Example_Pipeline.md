export REPO_NAME='[GitHub Repo Name]'
export JOB_NAME='Verify_Build_on_PR'
TEST_ERROR=0

cd Frontend
ng build || TEST_ERROR=$?
if [ $TEST_ERROR -eq 0 ] ; then
	cd ../Backend
  echo "PORT=3000" > .env
	npm run webpack || TEST_ERROR=$?
fi
cd ..


if [ $TEST_ERROR -eq 0 ] ; then
    curl "https://api.GitHub.com/repos/$REPO_NAME/statuses/$GIT_COMMIT" \
    -H "Content-Type: application/json" \
    -H "Authorization: token $GH_TOKEN" \
    -X POST \
    -d "{
        \"state\": \"success\",
        \"context\": \"jenkins/$REPO_NAME\",
        \"description\": \"Jenkins\",
        \"target_url\": \"https://ci.betterzon.xyz/job/$JOB_NAME/$BUILD_NUMBER/console\"
    }"
else
    curl "https://api.GitHub.com/repos/$REPO_NAME/statuses/$GIT_COMMIT" \
    -H "Content-Type: application/json" \
    -H "Authorization: token $GH_TOKEN" \
    -X POST \
    -d "{
        \"state\": \"failure\",
        \"context\": \"jenkins/$REPO_NAME\",
        \"description\": \"Jenkins\",
        \"target_url\": \"https://ci.betterzon.xyz/job/$JOB_NAME/$BUILD_NUMBER/console\"
    }"


    exit $TEST_ERROR
fi
