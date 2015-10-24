Configure `hosts` inventory file

Run ad-hoc command

    ansible -m ping hostname
    ansible -m setup hostname

Run the playbook

    ansible-playbook bootstrap.yml
    ansible-playbook run-nginx-proxy.yml
    ansible-playbook run-jenkins.yml
    ansible-playbook run-postgres.yml

