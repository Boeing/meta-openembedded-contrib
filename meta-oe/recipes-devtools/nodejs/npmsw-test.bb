inherit npm
LICENSE = "CLOSED"
SRC_URI = " \
    file://npm-local-link-sources/ \
    npmsw://${THISDIR}/${BPN}/npm-local-link-sources/inner/npm-shrinkwrap-v3.json \
"
S = "${WORKDIR}/npm-local-link-sources/inner"
