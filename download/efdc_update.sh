#!/bin/bash
# Usage: /bin/bash efdc_update.sh <netcdf_path> <portal_root_url>
# Example: /bin/bash efdc_update.sh /home/seanl/tmp/efdc http://staging.dc2b.intersect.org.au
EFDC_DIR="$1"
EFDC_FILE="EFDC_UoS-SHED_`date --date='yesterday' +%F`.nc"
PORTAL_ROOT="$2"

if [ -s $EFDC_DIR/$EFDC_FILE ]; then
	echo "Update metadata for $EFDC_DIR/$EFDC_FILE"
	TIMESTAMP=`date --date='yesterday' +'%d-%m-%Y+%T'`
	curl --verbose --request POST -d "collection_from_date=$TIMESTAMP&collection_to_date=$TIMESTAMP&token=3F2504E0-4F89-11D3-9A0C-0305E82C3301" $PORTAL_ROOT/metadata/updateNetCDFMetadata
else
	echo "No update needed"
fi