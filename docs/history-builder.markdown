# Build history

    for id in `git tag --sort=taggerdate`; do
      NAME=`echo $id | sed 's/^.//'`
      COMMIT=`git rev-list $id -n 1`
      DATE=`git log -n 1 --pretty=format:%cI $id`
      echo "{\"commitId\": \"$COMMIT\", \"version\": \"$NAME\", \"createdAt\": \"$DATE\" },"
    done

POST /api/repo

    {
      "name": "pity/pity",
      "url": "git@github.com:pity/pity.git",
      "bumper": "semver",
      "history": [ ... ]
    }
