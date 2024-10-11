#!/bin/bash

latest_tag=$(git describe --tags --abbrev=0)

if [ -z "$latest_tag" ]; then
  echo "No tags found!"
  exit 1
fi

output_file="diff_file.patch"

echo "Generating diff between the latest tag ($latest_tag) and the current commit..."
git diff "$latest_tag" HEAD > "$output_file"

if [ -f "$output_file" ]; then
  echo "Diff saved to $output_file"
else
  echo "Failed to generate the diff!"
fi
