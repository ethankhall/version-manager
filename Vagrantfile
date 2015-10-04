# -*- mode: ruby -*-
# vi: set ft=ruby :

Vagrant.configure(2) do |config|
  config.vm.box = "boxcutter/centos71"

  # Create a private network, which allows host-only access to the machine
  # using a specific IP.
  config.vm.network "private_network", ip: "172.0.1.100"

  config.berkshelf.berksfile_path = "chef/Berksfile"
  config.berkshelf.enabled = true

  config.vm.provision :chef_solo do |chef|
    chef.add_recipe "base-packages"
    chef.add_recipe "ehdev-postgresql"
    chef.add_recipe "ehdev-java"
    chef.json = {
      postgresql: {
        databases: {
          version_manager: {
            username: "version",
            password: "manager"
          }
        },
        password: {
          postgres: "iloverandompasswordsbutthiswilldo"
        }
      }
    }
  end
end
