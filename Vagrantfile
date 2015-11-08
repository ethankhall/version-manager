# -*- mode: ruby -*-
# vi: set ft=ruby :

Vagrant.configure(2) do |config|
  config.vm.box = "boxcutter/centos71"

  # Create a private network, which allows host-only access to the machine
  # using a specific IP.
  config.vm.network "private_network", ip: "172.0.1.100"

  config.berkshelf.berksfile_path = "Berksfile"
  config.berkshelf.enabled = true

  config.vm.provider "vmware_fusion" do |v|
    v.vmx["memsize"] = "8192"
    v.vmx["numvcpus"] = "8"
  end

  config.vm.provision :chef_solo do |chef|
    chef.add_recipe "base-packages"
    chef.add_recipe "ehdev-postgresql"
    chef.add_recipe "ehdev-java"
    chef.json = {
      postgresql: {
        password: {
          postgres: "iloverandompasswordsbutthiswilldo"
        }
      },
      webapp: {
        version_manager: {
          port: 8000,
          database: {
            name: 'version_manager',
            username: 'version',
            password: 'manager',
          }
        },
        version_manager_test: {
          port: 8001,
          database: {
            name: 'version_manager_test',
            username: 'version_manager_test',
            password: 'password',
          }
        }
      }
    }
  end
end
