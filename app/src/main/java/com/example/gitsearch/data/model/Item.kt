package com.example.gitsearch.data.model

import com.squareup.moshi.Json

data class ItemsResponse (@Json(name= "items") val items : List<Item> )

data class Item (
	@Json(name= "id") val id : Int,
	@Json(name= "node_id") val node_id : String,
	@Json(name= "name") val name : String,
	@Json(name= "full_name") val full_name : String,
	@Json(name= "private") val private : Boolean,
	@Json(name= "owner") val owner : Owner,
	@Json(name= "html_url") val html_url : String,
	@Json(name= "description") val description : String,
	@Json(name= "fork") val fork : Boolean,
	@Json(name= "url") val url : String,
	@Json(name= "forks_url") val forks_url : String,
	@Json(name= "keys_url") val keys_urls_url : String,
	@Json(name= "collaborators_url") val collaborators_url : String,
	@Json(name= "teams_url") val teams_url : String,
	@Json(name= "hooks_url") val hooks_url : String,
	@Json(name= "issue_events_url") val issue_events_url : String,
	@Json(name= "events_url") val events_url : String,
	@Json(name= "assignees_url") val assignees_url : String,
	@Json(name= "branches_url") val branches_url : String,
	@Json(name= "tags_url") val tags_url : String,
	@Json(name= "blobs_url") val blobs_url : String,
	@Json(name= "git_tags_url") val git_tags_url : String,
	@Json(name= "git_refs_url") val git_refs_url : String,
	@Json(name= "trees_url") val trees_url : String,
	@Json(name= "statuses_url") val statuses_url : String,
	@Json(name= "languages_url") val languages_url : String,
	@Json(name= "stargazers_url") val stargazers_url : String,
	@Json(name= "contributors_url") val contributors_url : String,
	@Json(name= "subscribers_url") val subscribers_url : String,
	@Json(name= "subscription_url") val subscription_url : String,
	@Json(name= "commits_url") val commits_url : String,
	@Json(name= "git_commits_url") val git_commits_url : String,
	@Json(name= "comments_url") val comments_url : String,
	@Json(name= "issue_comment_url") val issue_comment_url : String,
	@Json(name= "contents_url") val contents_url : String,
	@Json(name= "compare_url") val compare_url : String,
	@Json(name= "merges_url") val merges_url : String,
	@Json(name= "archive_url") val archive_url : String,
	@Json(name= "downloads_url") val downloads_url : String,
	@Json(name= "issues_url") val issues_url : String,
	@Json(name= "pulls_url") val pulls_url : String,
	@Json(name= "milestones_url") val milestones_url : String,
	@Json(name= "notifications_url") val notifications_url : String,
	@Json(name= "labels_url") val labels_url : String,
	@Json(name= "releases_url") val releases_url : String,
	@Json(name= "deployments_url") val deployments_url : String,
	@Json(name= "created_at") val created_at : String,
	@Json(name= "updated_at") val updated_at : String,
	@Json(name= "pushed_at") val pushed_at : String,
	@Json(name= "git_url") val git_url : String,
	@Json(name= "ssh_url") val ssh_url : String,
	@Json(name= "clone_url") val clone_url : String,
	@Json(name= "svn_url") val svn_url : String,
	@Json(name= "homepage") val homepage : String,
	@Json(name= "size") val size : Int,
	@Json(name= "stargazers_count") val stargazers_count : Int,
	@Json(name= "watchers_count") val watchers_count : Int,
	@Json(name= "language") val language : String,
	@Json(name= "has_issues") val has_issues : Boolean,
	@Json(name= "has_projects") val has_projects : Boolean,
	@Json(name= "has_downloads") val has_downloads : Boolean,
	@Json(name= "has_wiki") val has_wiki : Boolean,
	@Json(name= "has_pages") val has_pages : Boolean,
	@Json(name= "forks_count") val forks_count : Int,
	@Json(name= "mirror_url") val mirror_url : String,
	@Json(name= "archived") val archived : Boolean,
	@Json(name= "disabled") val disabled : Boolean,
	@Json(name= "open_issues_count") val open_issues_count : Int,
	@Json(name= "allow_forking") val allow_forking : Boolean,
	@Json(name= "is_template") val is_template : Boolean,
	@Json(name= "topics") val topics : List<String>,
	@Json(name= "visibility") val visibility : String,
	@Json(name= "forks") val forks : Int,
	@Json(name= "open_issues") val open_issues : Int,
	@Json(name= "watchers") val watchers : Int,
	@Json(name= "default_branch") val default_branch : String,
	@Json(name= "score") val score : Int
)