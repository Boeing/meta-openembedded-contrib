LICENSE = "CLOSED"

SRCREV = "b63c3934878be75e53713c5ec1cae3d12a87088d"
SRC_URI = "git://github.com/shr-project/com.webos.app.minimal;protocol=https;branch=webpack \
    npmsw://${THISDIR}/${BPN}/npm-shrinkwrap.json;destsuffix=git \
"
S = "${WORKDIR}/git"

DEPENDS = "nodejs-native"

do_configure() {
    :
}

do_compile() {
    :
}

do_install() {
    export UV_USE_IO_URING=0
    ${STAGING_BINDIR_NATIVE}/node node_modules/webpack-cli/bin/cli.js -o ${D}/test
}

FILES:${PN} += "test"

# to make sure this isn't cached in sstate-cache after showing just a warning in package_qa
# http://errors.yoctoproject.org/Errors/Details/739941/
ERROR_QA:append = " host-user-contaminated"

# buildpaths in "sources" tmp/work/core2-64-oe-linux/com.webos.app.minimal/1.0/package/test/main.js.map
# https://github.com/webpack/webpack/issues/15274
ERROR_QA:remove = " buildpaths"
