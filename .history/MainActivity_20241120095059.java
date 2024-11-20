ListView historyListView = findViewById(R.id.history_listview);
historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // 获取点击的历史记录项
        String selectedItem = (String) parent.getItemAtPosition(position);
        
        // 假设你有一个显示计算结果的TextView
        TextView displayTextView = findViewById(R.id.display_textview);
        
        // 将选中的历史记录显示在显示器上
        displayTextView.setText(selectedItem);
    }
}); 