Infrastructure
==============

Ansible playbooks and Docker containers to setup the Customer Service infrastructure. Precondition is a Debian 8 server accessible via SSH.

Description of the playbooks:

* `bootstrap` installs Docker on the host.
* `run-nginx-proxy` runs jwilder/nginx-proxy, this allows to proxy any subdomain to another Docker container. Requires a wildcard DNS record pointing to the host. It also supports htpasswd protection and SSL.
* `run-jenkins` runs a Jenkins CI server. The original Jenkins Docker image is extended to pre-install Java, Maven and additional plugins and to create an intitial seed job.
* `run-postgres` runs a PostgreSQL database that is used by the later deployed Customer Service.

Setup
-----

Configure `hosts` inventory file.

Test ad-hoc commands:

    ansible -m ping hostname
    ansible -m setup hostname

Play the books:

    ansible-playbook bootstrap.yml
    ansible-playbook run-nginx-proxy.yml
    ansible-playbook run-jenkins.yml
    ansible-playbook run-postgres.yml

