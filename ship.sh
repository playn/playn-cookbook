#!/bin/sh

set -e
DIR=$1
if [ ! -d "$DIR" ]; then
    echo "Usage: $0 category/exampledir"
    exit 255
fi

HTMLDIR=$DIR/html/target/*-html-1.0-SNAPSHOT
if [ ! -d $HMLDIR ]; then
    echo "Missing $HTMLDIR"
    exit 255
fi

# clean out cruft
rm -rf $HTMLDIR/META-INF
rm -rf $HTMLDIR/WEB-INF

# copy the non-cruft to samskivert.com
EXAMPLE=`basename $DIR`
CATDIR=`dirname $DIR`
CATEGORY=`basename $CATDIR`
rsync -arv $HTMLDIR/* samskivert.com:/export/samskivert/pages/code/playn-cookbook/$CATEGORY/$EXAMPLE
