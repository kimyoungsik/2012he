//
//  SVDViewController.h
//  ipadress
//
//  Created by developer on 12. 1. 9..
//  Copyright (c) 2012 HeadFlow All rights reserved.
//

#import <UIKit/UIKit.h>
 
@interface SVDViewController : UIViewController <UIActionSheetDelegate>
@property (retain, nonatomic) IBOutlet UITextField *ipAddress;
@property (retain, nonatomic) IBOutlet UITextField *port;
@property (retain, nonatomic) IBOutlet UIButton *connect;



- (IBAction)textFieldDoneEditing:(id)sender;
- (IBAction)backgroundTap:(id)sender;
@end
