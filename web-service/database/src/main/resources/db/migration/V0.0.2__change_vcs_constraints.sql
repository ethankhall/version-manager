ALTER TABLE vcs_repo_data DROP CONSTRAINT "vcs_repo_data_repo_name_key";
ALTER TABLE vcs_repo_data ADD CONSTRAINT vcs_repo_data_repo_name_url_key UNIQUE (repo_name, url);
