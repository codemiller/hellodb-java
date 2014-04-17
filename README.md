To get this app running on OpenShift, [sign up for OpenShift Online](https://openshift.redhat.com/app/account/new), [install the RHC command line tools](https://www.openshift.com/developers/rhc-client-tools-install), and run the following commands:

    rhc setup
    rhc app create hellodb jbossas-7 --from-code=https://github.com/codemiller/hellodb-java.git

The OpenShift `jbossas` cartridge documentation can be found at:

https://github.com/openshift/origin-server/tree/master/cartridges/openshift-origin-cartridge-jbossas/README.md
