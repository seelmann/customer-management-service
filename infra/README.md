Configure `hosts` inventory file

Run ad-hoc command

    ansible -i hosts -m ping hostname
    ansible -i hosts -m setup hostname

Run the playbook

    ansible-playbook -i hosts bootstrap.yml
    ansible-playbook -i hosts run-nginx-proxy.yml
    ansible-playbook -i hosts run-jenkins.yml

